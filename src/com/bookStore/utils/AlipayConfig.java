package com.bookStore.utils;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016091400512825";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCzimPgNyKpx1AdwDNpkuvmXAoojrQGkOO7T3C19QZC0lVwOImCC7WvXf1xkD0X6PgW5L9a6i7R8sbC4IAdq5DTBLXjB/lrROffV9Q9b0sfr/5a7JYLssrS27mSxhIgnK0hEJWcW0U2vvbCKwiNsjP7anI05Rc3ft+6oYgABZ/6xbcddeyQatJ3JiYLvDkAzCcKB7iJsVcbx4JFiZPddjQVVyEpl3XvXsnmaWr7WUbwNn4jZR+IE//b0E8Adp3NMvyR1dPVwmwC5pVpdrHqU9jB+hp8S+FvDmlTO6MDrliDfzFrLNZcEQysmdfgcLqJJKcEdE51T+DY8egbHHFo283JAgMBAAECggEAcHR5FGJUctUliEzvpuvgmBwEPN0NQ6OTEKq0ZTQA5O/LlgvDjVH6TcpoSa5XpddFNUm31aEjDFutRTjK5kqk3pdvubEv95FkTs87X/OKsWFRxu+zF8a4JZR8ysMHbafVBQXXP+UnGCBKfNnpdJ8u0gYdXKY1gYwVZFdy0Ni1GKOZdGbCdkFeLbZxPBe0XZ86ed8z6PXwNysDRCOmh7w8RtJz5MGWjMCYe3GCz8HgQB46C1pK5dKuRKoCXVP6W3diX+bIvDXR/7W6MbxWHkTRIJAOqT4wAkS53CuvG2qtFzUPxSSzj5v62mQdMEkNaF1zYwiLt2AVG+tCvrnTULw6kQKBgQD12nbd0DsgvbR2aDQPBJmDD+U1fJ3fVyT3/LlIMfdikLpWv5swwID+m/TP7OpPwOiFvrFAZ7ebcH6Heuc+FQvXUuNTQtkaUYMHTH/YPYl4HhX739E8rAjgbGVNXzK8aTs4k8gl8wuYwWU13UAp5oVtM/haMCtJWsMb2aQgajr1/QKBgQC6804+H2llu5XmxzRrCRdRaxj9fxqurFLl5AOyzk8HSDbR/W/hL4uVcfHZx+nkZPdbgwp09m39rx05gmos6hostyKYARZYUA7u7/cINSNwERpGh2UDlyTEH6kOYlvwG7lRNFA/dCf+XZnaYZh3VNRTE0IecQLpc8pjDAOJ4uKavQKBgQCF7H+bzPaomy5W4eSZ2bOWBW6ieEO3ic0FmZGEllNgSd53quBq6jJaf7IYIcJxt2/yizIbF2SZM+p4+qZE10of/XL/xAZno8dwjmeF79+Woyzui7l7B2u+dM2qHBWTNO+hnj8FDSRgDdmIQgHV0VEVb5314w9xYhOudBxH3BZKMQKBgQCALPK3KwgzROKowGmS/T+shdYzb2LW4bVQgv16KXMLlhpn7PA5H0T/aIkd/K/C9OqXJogT2yVcaiSKWnz7YGIjyEgCmbQ9Rl6GuaXQTcaHPmKaepfqV0VQ5jgdcZiBTjDHmStv5e7JulkS3hkPr+yHLMwHXQpXDxlywTjn39PnrQKBgD/6cfD5CgeHu1KsvRYCKrOwsE+2tPl4J9LOZjz4XGsMehm8cnNTi2zpe9vWM4kJhY0iH+y4zGRbgf1sb1q/WaFrdtRNxivqqgP7iB+nfOMJGz/PkhOl5xrzjzUbj++JAveZC+IJAoTWwSz12blw68JtyB9YBfXaKbFfB1C414Su";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAroHdc4Q3g3zR2CHASHF+BpxFFZ8/KL/sgidEREg93e0es5quqVHVuRFoko8XkRrwH47qjnpu4HpH6yw+/TDFCJtiueWqV6TIMr4zQRvUpLQGyJ+bsqoiOd7SS34VbWJAjUZfcq65L8pkblqBNaxS8JcZcMt7Yq5Vu3bI99r/wUBdK6wo+o3OgZgkORTf+/f5qKVlElMnauLE+N3c1KqPLKJXKEZn+4maZeBOaP+tNGmN9IDsrFn0wgne8P3U/DpCZ7YukYu7R6ul6SBkVxRkE1uoo3DlYRCCdTCKW7/qZ0ZUCMvv2W9MVUDH8K1MndDwCeczm6vyNKdEbEv+/MQMpQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://localhost:8080/bookstore01/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://localhost:8080/bookstore01/client/cart/paysuccess.do";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl ="https://openapi.alipaydev.com/gateway.do";
	
	// 支付宝网关
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

