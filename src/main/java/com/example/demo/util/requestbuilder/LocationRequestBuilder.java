package com.example.demo.util.requestbuilder;

import com.example.msfaastransactionlocation.dto.request.transactionrequest.TransactionRequest;
import com.example.msfaastransactionlocation.util.logging.Logging;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.StringWriter;


@Service
@RequiredArgsConstructor
@SuppressWarnings("java:S2440")
public class LocationRequestBuilder {


    private final Logging logging = new Logging();

    public String locationReq(String msisdn, String requestId, String timestamp, String  remoteAddress, String amount, String otp) {

        //Build Query Customer Products Request Class
        long processStartTime = System.currentTimeMillis();

        TransactionRequest transactionRequest = new TransactionRequest(
                new TransactionRequest.BodyMain(
                        new TransactionRequest.CustomerValidateRequest(
                                msisdn,
                                requestId,
                                timestamp,
                                remoteAddress,
                                amount,
                                otp

                        )
                )
        );




        //Create XML String Request

        return getString(transactionRequest,processStartTime);
    }



    private String getString(TransactionRequest transactionRequest, long processStartTime) {
        try {
            StringWriter outputConfirmation = new StringWriter();

            JAXBContext ctx = JAXBContext.newInstance(TransactionRequest.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.marshal(transactionRequest, outputConfirmation);

            return outputConfirmation.toString();
        } catch (JAXBException e) {
            logging
                    .setLogLevel("error")
                    .setTransactionID(transactionRequest.bodyMain.customerValidateRequest.customerNumber)
                    .setProcess("Building Location Request")
                    .setProcessDuration(System.currentTimeMillis() - processStartTime)
                    .setRequest(transactionRequest)
                    .setResponseMsg(e.getMessage())
                    .write();
            return null; // Or return an appropriate default/fallback value
        }
    }


}
