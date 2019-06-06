package ru.spbau

import com.amazonaws.client.builder.AwsClientBuilder
import com.amazonaws.services.sqs.AmazonSQS
import com.amazonaws.services.sqs.AmazonSQSClientBuilder
import com.amazonaws.services.sqs.model.*
import kotlin.system.exitProcess

fun createQueueIfNotExists(sqs: AmazonSQS, name: String) {
    val createRequest = CreateQueueRequest(name)
        .addAttributesEntry("DelaySeconds", "1")
        .addAttributesEntry("MessageRetentionPeriod", "60")

    try {
        sqs.createQueue(createRequest)
    } catch (e: AmazonSQSException) {
        if (e.errorCode != "QueueAlreadyExists") {
            e.printStackTrace()
            exitProcess(-1)
        }
    }
}

fun init(): AmazonSQS {
    System.setProperty("aws.accessKeyId", "ak")
    System.setProperty("aws.secretKey", "sk")
    return AmazonSQSClientBuilder.standard().withEndpointConfiguration(
        AwsClientBuilder.EndpointConfiguration("http://localstack:4576", "eu-west-1")
    ).build()
}

fun receive(sqs: AmazonSQS, queue: String): List<Int> {
    val messages = sqs.receiveMessage(ReceiveMessageRequest(queue)).messages
    messages.forEach {
        sqs.deleteMessage(DeleteMessageRequest(queue, it.receiptHandle))
    }
    return messages.map { it.body.toInt() }
}

fun send(sqs: AmazonSQS, queue: String, value: Int) {
    sqs.sendMessage(SendMessageRequest(queue, value.toString()))
}

fun main(args: Array<String>) {
    val sqs = init()
    createQueueIfNotExists(sqs, args[0])
    createQueueIfNotExists(sqs, args[1])
    val queueA = sqs.getQueueUrl(GetQueueUrlRequest(args[0])).queueUrl
    val queueB = sqs.getQueueUrl(GetQueueUrlRequest(args[1])).queueUrl
    if (args.size > 2) {
        send(sqs, queueB, args[2].toInt())
    }
    while (true) {
        receive(sqs, queueA).forEach {
            println("$queueA: $it")
            send(sqs, queueB, it + 1)
        }
    }
}
