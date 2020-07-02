package cn.edu.hbuas.examsys.utils;

import cn.edu.hbuas.examsys.mybatis.pojo.Note;
import cn.edu.hbuas.examsys.mybatis.pojo.TeacherArrange;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 短信发送工具类
 * @author 牛传喜
 */
public class NoteUtil {
    private static Logger logger = LoggerFactory.getLogger(NoteUtil.class);

    private static String[] TEMP_CODE = {"SMS_167531280", "SMS_167526430", "SMS_167526436","SMS_167526440"};

    private static String[][] params = {{"firstExamTime", "firstRoomPlace", "firstExamName"},
            {"secondExamTime", "secondRoomPlace", "secondExamName"},
            {"thirdExamTime", "thirdRoomPlace", "thirdExamName"},
            {"fourthExamTime", "fourthRoomPlace", "fourthExamName"}};
        //    name-其他；examDate-时间；firstExamTime-时间；firstRoomPlace-其他；firstExamName-其他；
        // secondExamTime-时间；secondRoomPlace-其他；secondExamName-其他；
        // thirdExamTime-时间；thirdRoomPlace-其他；thirdExamName-其他；
        // fourthExamTime-时间；fourthRoomPlace-其他；fourthExamName-其他；

    /**
     * 通用的发送短信方法
     * @param note 发送的短信类封装
     * @param size 监考场数
     */
    public static void generallySendNote(Note note, int size) {
        //准备工作
        String phone = note.getPhone();
        //短信模板的ID
        String tmpCode = TEMP_CODE[size - 1];
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name",note.getName());
        jsonObject.put("examDate",note.getTeacherArrangeList().get(0).getExamTime());
        List<TeacherArrange> teacherArrangeList = note.getTeacherArrangeList();
        TeacherArrange teacherArrange;
        for (int i = 0; i < size; i++) {
            teacherArrange = teacherArrangeList.get(i);
            jsonObject.put(params[i][0],teacherArrange.getBegin()+"-"+teacherArrange.getEnd());
            jsonObject.put(params[i][1],teacherArrange.getRoomPlace());
            jsonObject.put(params[i][2],teacherArrange.getExamName());
        }


        //准备发送
        DefaultProfile profile = DefaultProfile.getProfile("default", "LTAIzR5cGewKCJ7T", "jFIGMvM5o9SMauwNYVu5KWlDzzcwbs");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();

        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "湖北文理学院教务处");
        request.putQueryParameter("TemplateCode", tmpCode);
        request.putQueryParameter("TemplateParam", jsonObject.toJSONString());
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

}
