// camel-k: dependency=camel-azure-storage-blob
//
// Apache Camel Azure Storage Blob Consumer
//
import static org.apache.camel.support.processor.idempotent.MemoryIdempotentRepository.memoryIdempotentRepository

from('knative:channel/azure-blob')
   .setHeader('orderId', xpath("/order/@id",String.class))
//use idempotent consumer to process an order only once
        .idempotentConsumer(header("orderId"), memoryIdempotentRepository(50))
        .log('Received:\n${body}')
        .split()
            .xpath('/order/item')
            .setHeader('lineItemId', xpath("/item/@id",String.class))
            .setHeader('CamelAzureStorageBlobBlobName', simple('${header.lineItemId}-${header.orderId}.xml'))
            .log('Uploading Item ${header.CamelAzureStorageBlobBlobName}:\n${body}')
            .to('azure-storage-blob:{{azure.blob.accountName}}/{{azure.blob.itemsContainerName}}?accessKey=RAW({{azure.blob.accessKey}})&operation=uploadBlockBlob')




