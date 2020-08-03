// camel-k: dependency=camel-azure-storage-queue
//
// Apache Camel Azure Storage Queue Producer
//
// This is just a sample producer for Azure that sends messages to an Azure storage queue

import static org.apache.camel.component.azure.storage.queue.QueueConstants.MESSAGE_TEXT

from('timer:java?period=3000&repeatCount=30')
        .setHeader(MESSAGE_TEXT, constant('Hello Camel K'))
        .to('azure-storage-queue:{{azure.queue.accountName}}/{{azure.queue.queueName}}?accessKey=RAW({{azure.queue.accessKey}})&operation=sendMessage')