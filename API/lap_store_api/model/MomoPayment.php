<?php
class MomoPayment {
    private $partnerCode = "MOMO";
    private $accessKey = "F8BBA842ECF85";
    private $secretKey = "K951B6PE1waDMi640xX08PD3vg6EkVlz";
    private $endpoint = "https://test-payment.momo.vn/v2/gateway/api/create";

    public function createPayment($amount, $orderInfo = "Thanh toán đơn hàng LapStore") {
        $orderId = time() . "";
        $requestId = $orderId;
        $redirectUrl = "mylapstore://paysuccess";
        $ipnUrl = "http://192.168.3.49/lap_store_api/api/ThanhToan/ipn_momo.php";
        $extraData = "";

        $rawHash = "accessKey={$this->accessKey}&amount=$amount&extraData=$extraData&ipnUrl=$ipnUrl&orderId=$orderId&orderInfo=$orderInfo&partnerCode={$this->partnerCode}&redirectUrl=$redirectUrl&requestId=$requestId&requestType=captureWallet";
        $signature = hash_hmac("sha256", $rawHash, $this->secretKey);

        $data = [
            'partnerCode' => $this->partnerCode,
            'accessKey' => $this->accessKey,
            'requestId' => $requestId,
            'amount' => strval($amount),
            'orderId' => $orderId,
            'orderInfo' => $orderInfo,
            'redirectUrl' => $redirectUrl,
            'ipnUrl' => $ipnUrl,
            'lang' => 'vi',
            'extraData' => $extraData,
            'requestType' => 'captureWallet',
            'signature' => $signature
        ];

        $ch = curl_init($this->endpoint);
        curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
        curl_setopt($ch, CURLOPT_HTTPHEADER, ['Content-Type: application/json']);
        curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($data));
        $response = curl_exec($ch);
        curl_close($ch);

        return json_decode($response, true);
    }
}
