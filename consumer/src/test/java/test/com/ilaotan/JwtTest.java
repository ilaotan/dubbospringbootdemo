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

        String jtwstr = "eyJhbGciOiJSUzI1NiJ9.eyJqdGkiOiJhYmMxMjM0NTY3OCIsImlhdCI6MTUyNjQ2NDAxOCwic3ViIjoiIiwiaXNzIjoiMTU2Mjg5ODYyMTQiLCJleHAiOjE1MjY0NjQzNzh9.U_R2bxSRl-1DqHbisPQsCiEXCdxi0hrU1kJEKSnGHcl1s8i_uZcnkk5NKYmlhBzJ39wFlOCHn2z5MvhltZQiaJZZeIJmd2eWeK1tYAzQSatxZxjeqH21NQEP2rEFSuPgNyUpEoTHRCAWgS07OO2SG0SBclIiyKf44a9fvZwHEMg";

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
