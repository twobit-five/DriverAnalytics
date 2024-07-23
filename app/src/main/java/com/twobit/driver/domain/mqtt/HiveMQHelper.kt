package com.twobit.driver.domain.mqtt

import android.util.Log
import com.hivemq.client.mqtt.MqttClient
import com.hivemq.client.mqtt.datatypes.MqttQos
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck
import java.nio.charset.StandardCharsets

class HiveMQHelper(
    serverUri: String,
    clientId: String,
    port: Int,
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

    private var isConnected = false

    fun connect() {
        if (!isConnected) {
            client.connectWith()
                .simpleAuth()
                .username(username)
                .password(password.toByteArray(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send()
                .whenComplete { connAck: Mqtt5ConnAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to connect to MQTT broker: ${throwable.message}")
                    } else {
                        Log.i("HiveMQHelper", "Connected to MQTT broker: ${connAck.toString()}")
                        isConnected = true
                    }
                }
        }
    }

    private fun ensureConnected(action: () -> Unit) {
        if (isConnected) {
            action()
        } else {
            connect()
            client.connectWith()
                .simpleAuth()
                .username(username)
                .password(password.toByteArray(StandardCharsets.UTF_8))
                .applySimpleAuth()
                .send()
                .whenComplete { connAck: Mqtt5ConnAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to reconnect to MQTT broker: ${throwable.message}")
                    } else {
                        Log.i("HiveMQHelper", "Reconnected to MQTT broker: ${connAck.toString()}")
                        isConnected = true
                        action()
                    }
                }
        }
    }

    fun connectAndPublish(topic: String, payload: String) {
        ensureConnected {
            publish(topic, payload)
        }
    }

    fun publish(topic: String, payload: String) {
        if (isConnected) {
            client.publishWith()
                .topic(topic)
                .payload(payload.toByteArray(StandardCharsets.UTF_8))
                .qos(MqttQos.AT_LEAST_ONCE)
                .send()
                .whenComplete { publish: Mqtt5PublishResult?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to publish message: ${throwable.message}")
                    } else {
                        Log.i("HiveMQHelper", "Message published to topic $topic: $payload")
                    }
                }
        } else {
            Log.e("HiveMQHelper", "Failed to publish message: MQTT client is not connected.")
        }
    }

    fun subscribe(topic: String) {
        ensureConnected {
            client.subscribeWith()
                .topicFilter(topic)
                .qos(MqttQos.AT_LEAST_ONCE)
                .callback { publish: Mqtt5Publish ->
                    Log.i("HiveMQHelper", "Received message: ${String(publish.payloadAsBytes, StandardCharsets.UTF_8)}")
                }
                .send()
                .whenComplete { subAck: Mqtt5SubAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to subscribe to topic $topic: ${throwable.message}")
                    } else {
                        Log.i("HiveMQHelper", "Subscribed to topic $topic")
                    }
                }
        }
    }

    fun unsubscribe(topic: String) {
        ensureConnected {
            client.unsubscribeWith()
                .topicFilter(topic)
                .send()
                .whenComplete { unsubAck: Mqtt5UnsubAck?, throwable: Throwable? ->
                    if (throwable != null) {
                        Log.e("HiveMQHelper", "Failed to unsubscribe from topic $topic: ${throwable.message}")
                    } else {
                        Log.i("HiveMQHelper", "Unsubscribed from topic $topic")
                    }
                }
        }
    }
}
