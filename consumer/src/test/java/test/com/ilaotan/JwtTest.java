package test.com.ilaotan;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import com.ilaotan.jwt.JwtTool;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.hibernate.validator.constraints.br.TituloEleitoral;
import org.junit.Test;

/**
 * 类的描述.
 *
 * @author tan liansheng on 2018/5/16 14:35
 */
public class JwtTest extends Base {



    @Autowired
    private JwtTool jwtTool;

    @Test
    public void getSign(){

        String sign = jwtTool.createJWT("abc12345678","15628986214", "", 36000L);
        System.out.println(sign);

    }

    @Test
    public void validate(){
        String str1 = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhYmMxMjM0NTY3OCIsImlhdCI6MTUyNjQ1Mjc2Niwic3ViIjoiIiwiaXNzIjoiMTU2Mjg5ODYyMTQiLCJleHAiOjE1MjY0NTI3NjZ9.DOEwPoupqNw1TlRD6ByOvHJ-sXocr8Lr6JMkPVYFrAM";

        try {
            System.out.println(jwtTool.parseJWT(str1));

        }catch (ExpiredJwtException e1){
            System.out.println("token过期了");
            return;
        }catch (MalformedJwtException e2){
            System.out.println("字符串不合法");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    ///////////////////////测试一下RSA的///////////////////////////////////////////


    @Test
    public void getSignRSA() throws IOException {

        String sign = jwtTool.createJWTRSA("abc12345678","15628986214", "", 360L);
        System.out.println(sign);

    }


    @Test
    public void validateRSA(){

        // http://www1.tc711.com/tool/BASE64.htm
        // 以 . 为界 前两组字符串都能解析出字符串来
        String jtwstr = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJhYmMxMjM0NTY3OCIsImlhdCI6MTUyNjQ2NTQxNiwic3ViIjoiIiwiaXNzIjoiMTU2Mjg5ODYyMTQiLCJleHAiOjE1MjY0NjU3NzZ9.Q6-olz1dGaPr0umCkL5xr27jDzpbC__uDnw9XRjv4Oby7pRK5M79hBFFyOpvfr5Dz-NdHQ7GIVWlfCtOjFCLw_edH91GuxZ6Hu--gL0v98euODAMUyJ7N7401O-xJ5FZCgbg_HRkCa6tARovBRhuozWuXwhSdJu7n4i3JGO4JjQ";
        try {
            System.out.println(jwtTool.parseJWTRSA(jtwstr));

        }catch (ExpiredJwtException e1){
            e1.printStackTrace();
            System.out.println("token过期了");
            return;
        }catch (MalformedJwtException e2){
            System.out.println("字符串不合法");
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
