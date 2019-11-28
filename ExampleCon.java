package io.bsa.modules.sys.newcontroller.ApiPushDataController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import java.util.Map;

@RestController
@RequestMapping("/receivesdata")
@Api(tags = "数据接收管理")
public class ExampleCon {


    /**
     * 功能描述：第三方平台数据接收。<p>
     *           <ul>注:
     *               <li>1.所需jar包或maven坐标需自己下载。</li>
     *               <li>2.物联平台为了保证数据不丢失，有重发机制，如果重复数据对业务有影响，数据接收端需要对重复数据进行排除重复处理。</li>
     *               <li>3.物联平台每一次post数据请求后，等待客户端的响应都设有时限，在规定时限内没有收到响应会认为发送失败。
     *                    接收程序接收到数据时，尽量先缓存起来，再做业务逻辑处理。</li>
     *           </ul>
     * @param body 数据消息
     * @return 公司主账号用户名或个人账号用户名。物联平台接收到http 200的响应，才会认为数据推送成功，否则会重发。
     */
    /**
     * 推送接口接口校验Get
     *
     * @author bosen
     * @since 2018年11月28日14:21:45
     */
    @ApiOperation("推送接口接口校验Get")
    @GetMapping("/receives")
    public String testGet(String msg_signature) {
        BASE64Encoder encoder = new BASE64Encoder();
        //平台上定义的token
        String token = "123456";
        //当前登录账号的用户名或公司主账号的用户名
        String username = "admin";
        token += username;
        String md5Format = DigestUtils.md5Hex(token);
        String encode = encoder.encode(md5Format.getBytes());
        if (encode.equals(msg_signature)) {
            //验证成功返回用户名
            return username;
        }
        return null;
    }

    /**
     * 推送接口接收数据Post,若十次请求无响应,则本次推送结束
     *
     * @author bosen
     * @since 2018年11月28日14:21:45
     */
    @ApiOperation("推送接口接收数据Post")
    @PostMapping("/receives")
    public String testPost(@RequestBody Map body) throws InterruptedException {
        JSONObject jsonMsg = new JSONObject(body);
        /*
        //可以进行二次验证,若不想验证可跳过该步骤
        String msg_signature = jsonMsg.getString("msg_signature");
        BASE64Encoder encoder = new BASE64Encoder();
        //平台上定义的token
        String token = "123456";
        //公司主账号用户名或个人账号用户名
        String username = "admin";
        token += username;
        String md5Format = DigestUtils.md5Hex(token);
        String encode = encoder.encode(md5Format.getBytes());
        if (encode.equals(msg_signature)) {
            //这里为拿到数据后的逻辑处理
            System.out.println(jsonMsg.toString());
        }*/

        //这里为拿到数据后的逻辑处理
        System.out.println(jsonMsg.toString());

        return "OK";
    }
}
