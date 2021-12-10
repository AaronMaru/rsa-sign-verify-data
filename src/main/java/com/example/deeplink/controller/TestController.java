package com.example.deeplink.controller;

import com.example.deeplink.reponse.TestRS;
import com.example.deeplink.request.Individual;
import com.example.deeplink.util.MaruRSA;
import kh.org.nbc.bakong_khqr.BakongKHQR;
import kh.org.nbc.bakong_khqr.model.IndividualInfo;
import kh.org.nbc.bakong_khqr.model.KHQRCurrency;
import kh.org.nbc.bakong_khqr.model.KHQRData;
import kh.org.nbc.bakong_khqr.model.KHQRResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class TestController {

    @Autowired
    private MaruRSA maruRSA;

    @PostMapping("test")
    public ResponseEntity<?> test(@RequestBody Individual individual) throws Exception {

        IndividualInfo individualInfo = new IndividualInfo();
        individualInfo.setBakongAccountId("john_smith@devb");
        individualInfo.setAccountInformation("85512233455");
        individualInfo.setAcquiringBank("Dev Bank");
        individualInfo.setCurrency(KHQRCurrency.USD);
        individualInfo.setAmount(100.0);
        individualInfo.setMerchantName("John Smith");
        individualInfo.setMerchantCity("PHNOM PENH");
        individualInfo.setBillNumber("#12345");
        individualInfo.setMobileNumber("85512233455");
        individualInfo.setStoreLabel("Coffee Shop");
        individualInfo.setTerminalLabel("Cashier_1");

        KHQRResponse<KHQRData> response = BakongKHQR.generateIndividual(individualInfo);
        if (response.getKHQRStatus().getCode() == 0) {
            System.out.println("data: " + response.getData().getQr());
            System.out.println("md5: " + response.getData().getMd5());
        }


//        String privateKey = maruRSA.getPrivateKey("./keys/private.key");
//
//        System.out.println(individual.toString() + "&signature=" + maruRSA.printPublicKey());

        TestRS testRS = new TestRS();
        testRS.setMerchantName(individual.getMerchantName());
        return new ResponseEntity<>(testRS, HttpStatus.OK);
    }

}
