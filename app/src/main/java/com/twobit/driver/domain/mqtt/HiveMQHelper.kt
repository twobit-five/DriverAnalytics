package com.twobit.driver.domain.mqtt

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean

class HiveMQHelper(
    private val serverUri: String,
    private val clientId: String,
    private val port: Int,
    private val username: String,
    private val password: String
) {
    private val client: Mqtt5AsyncClient = MqttClient.builder()
        .useMqttVersion5()
        .identifier(clientId)
        .serverHost(serverUri)
        .automaticReconnectWithDefaultConfig()
        .serverPort(port)
        .buildAsync()

    private val isConnected = AtomicBoolean(false)
    private val isConnecting = AtomicBoolean(false)

    @RequiresApi(Build.VERSION_CODES.S)
    fun connect() {
        if (isConnecting.get()) {
            Log.i("HiveMQHelper", "Already attempting to connect to MQTT broker.")
            return
        }

        isConnecting.set(true)
        Log.i("HiveMQHelper", "Attempting to connect to MQTT broker...")

        try {
            client.connectWith()
                .simpleAuth()
                .username(username)
                .password(password.toByteArray(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send()
                .orTimeout(10, TimeUnit.SECONDS)
                .whenComplete { connAck: Mqtt5ConnAck?, throwable: Throwable? ->
                    isConnecting.set(false)
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to connect to MQTT broker: ${throwable.message}", throwable)
                        isConnected.set(false)
                    } else {
                        Log.i("HiveMQHelper", "Connected to MQTT broker: $connAck")
                        isConnected.set(true)
                    }
                }
        } catch (e: Exception) {
            Log.e("HiveMQHelper", "Failed to connect to MQTT broker: ${e.message}", e)
            isConnected.set(false)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    fun connectAndPublish(topic: String, payload: String) {
        ensureConnected()
        publish(topic, payload)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun ensureConnected() {
        if (!isConnected.get()) {
            Log.i("HiveMQHelper", "Not connected. Attempting to reconnect...")
            reconnect(5)
            if (!isConnected.get()) {
                throw Exception("Failed to connect to MQTT broker after retrying.")
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun reconnect(retryCount: Int) {
        var attempts = 0
        while (attempts < retryCount && !isConnected.get()) {
            Log.i("HiveMQHelper", "Reconnection attempt ${attempts + 1} of $retryCount")
            connect()
            Thread.sleep(2000)
            attempts++
        }
    }

    private fun publish(topic: String, payload: String) {
        if (isConnected.get()) {
            Log.i("HiveMQHelper", "Publishing message to topic $topic...")
            client.publishWith()
                .topic(topic)
                .payload(payload.toByteArray(StandardCharsets.UTF_8))
                .qos(MqttQos.AT_LEAST_ONCE)
                .send()
                .whenComplete { publishResult: Mqtt5PublishResult?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to publish message: ${throwable.message}", throwable)
                    } else {
                        Log.i("HiveMQHelper", "Message published to topic $topic: $payload")
                    }
                }
        } else {
            Log.e("HiveMQHelper", "Failed to publish message: MQTT client is not connected.")
        }
    }

    fun subscribe(topic: String) {
        if (isConnected.get()) {
            Log.i("HiveMQHelper", "Subscribing to topic $topic...")
            client.subscribeWith()
                .topicFilter(topic)
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback { publish ->
                    Log.i("HiveMQHelper", "Received message: ${String(publish.payloadAsBytes, StandardCharsets.UTF_8)}")
                }
                .send()
                .whenComplete { subAck: Mqtt5SubAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to subscribe to topic $topic: ${throwable.message}", throwable)
                    } else {
                        Log.i("HiveMQHelper", "Subscribed to topic $topic")
                    }
                }
        } else {
            Log.e("HiveMQHelper", "Cannot subscribe, not connected to MQTT broker.")
        }
    }

    fun unsubscribe(topic: String) {
        if (isConnected.get()) {
            Log.i("HiveMQHelper", "Unsubscribing from topic $topic...")
            client.unsubscribeWith()
                .topicFilter(topic)
                .send()
                .whenComplete { unsubAck: Mqtt5UnsubAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to unsubscribe from topic $topic: ${throwable.message}", throwable)
                    } else {
                        Log.i("HiveMQHelper", "Unsubscribed from topic $topic")
                    }
                }
        } else {
            Log.e("HiveMQHelper", "Cannot unsubscribe, not connected to MQTT broker.")
        }
    }
}
