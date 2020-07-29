// camel-k: dependency=camel-azure-storage-blob
//
// Apache Camel Azure Storage Blob Producer
//
// This is just a sample producer for Azure that creates a blob in a container
import com.azure.storage.blob.models.BlobStorageException

from('timer:java?period=3000&repeatCount=1')
//create needed containers if not exist
        .doTry()
            .to('azure-storage-blob:{{azure.blob.accountName}}/{{azure.blob.ordersContainerName}}?accessKey=RAW({{azure.blob.accessKey}})&operation=createBlobContainer')
        .doCatch(BlobStorageException.class)
            .log('{{azure.blob.ordersContainerName}} container already exist! ')
        .end()
        
        .doTry()
            .to('azure-storage-blob:{{azure.blob.accountName}}/{{azure.blob.itemsContainerName}}?accessKey=RAW({{azure.blob.accessKey}})&operation=createBlobContainer')
        .doCatch(BlobStorageException.class)
            .log('{{azure.blob.itemsContainerName}} container already exist!')
        .end()
//send the order
        .setBody()
        .constant('''
<?xml version="1.0" encoding="UTF-8"?>
<order id="5ece4797eaf5e">
    <item id="item1">
        <name>Precision</name>
        <mfg>Fender</mfg>
    </item>
    <item id="item2">
        <name>Jazz</name>
        <mfg>Fender</mfg>
    </item>
    <item id="item3">
        <name>Thunderbird</name>
        <mfg>Gibson</mfg>
    </item>
    <item id="item4">
        <name>Ripper</name>
        <mfg>Gibson</mfg>
    </item>
    <item id="item5">
        <name>4003</name>
        <mfg>Rickenbacker</mfg>
    </item>
    <item id="item6">
        <name>Metroline</name>
        <mfg>Sadowsky</mfg>
    </item>
    <item id="item7">
        <name>Imperial Elite</name>
        <mfg>Fodera</mfg>
    </item>
    <item id="item8">
        <name>Yin Yang Std 4</name>
        <mfg>Fodera</mfg>
    </item>
    <item id="item9">
        <name>BN5</name>
        <mfg>Furlanetto</mfg>
    </item>
    <item id="item10">
        <name>VF4-P</name>
        <mfg>Furlanetto</mfg>
    </item>
</order>
''')
    .to('azure-storage-blob:{{azure.blob.accountName}}/{{azure.blob.ordersContainerName}}?accessKey=RAW({{azure.blob.accessKey}})&blobName={{azure.blob.blobName}}&operation=uploadBlockBlob')
    .log('Message sent: ${body}')
