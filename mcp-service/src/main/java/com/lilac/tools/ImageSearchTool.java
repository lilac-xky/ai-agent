package com.lilac.tools;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 图片搜索工具类
 */
@Service
public class ImageSearchTool {

    @Value("${pexels.api}")
    private String API_KEY;
    
    private static final String API_URL = "https://api.pexels.com/v1/search";

    /**
     * 搜索图片
     *
     * @param query 搜索关键字
     * @return 图片链接列表
     */
    @Tool(description = "search image from web")
    public String searchImage(@ToolParam(description = "Search query keyword") String query) {
        try {
            return String.join(",", searchMediumImages(query));
        } catch (Exception e) {
            return "Error search image: " + e.getMessage();
        }
    }

    /**
     * 搜索中等分辨率的图片
     *
     * @param query 搜索关键字
     * @return 图片链接列表
     */
    public List<String> searchMediumImages(String query) {
        // 创建 HTTP 请求头
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", API_KEY);
        // 创建 HTTP 请求参数
        Map<String, Object> params = new HashMap<>();
        params.put("query", query);
        // 发送 HTTP GET 请求
        String response = HttpUtil.createGet(API_URL)
                .addHeaders(headers)
                .form(params)
                .execute()
                .body();
        // 解析响应
        return JSONUtil.parseObj(response)
                .getJSONArray("photos")
                .stream()
                .map(photoObj -> (JSONObject) photoObj)
                .map(photoObj -> photoObj.getJSONObject("src"))
                .map(photo -> photo.getStr("medium"))
                .filter(StrUtil::isNotBlank)
                .collect(Collectors.toList());
    }
}
