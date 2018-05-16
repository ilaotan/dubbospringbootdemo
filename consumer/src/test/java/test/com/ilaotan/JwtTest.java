package test.com.ilaotan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;

import com.ilaotan.jwt.JwtTool;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
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

        String sign = jwtTool.createJWT("abc12345678","15628986214", "", 60L);
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
            System.out.println("json串不合法");
        }
        catch (Exception e){
            e.printStackTrace();
        }



    }

}
