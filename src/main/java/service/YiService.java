package service;

import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.sql.Timestamp;

@Service
public class YiService {

    public YiService() {
        
    }

    // @Tool(description = "根据给定的生辰八字来算卦")
    // public String getWeatherForecastByLocation(
    //         @ToolParam(description = "一个ISO 8601 标准格式的日期时间(eg. '2025-03-11T14:32:01Z')")  String datetime)
    // {
    //     return "";
    // }

    @Tool(description = "")
    public String getAlerts(
            @ToolParam(description = "Two-letter US state code (e.g. CA, NY")  String state)
    {
        return "";
    }

    // ......
}
