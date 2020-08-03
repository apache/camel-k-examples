// camel-k: dependency=camel-azure-storage-queue
//
// Apache Camel Azure Storage Queue Consumer
//

from('knative:channel/azure-queue')
        .log('Received: ${body}')


