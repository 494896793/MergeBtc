package com.bochat.app.app.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bochat.app.R;
import com.bochat.app.common.util.ALog;
import com.bochat.app.common.util.SharePreferenceUtil;
import com.bochat.app.model.util.Api;
import com.bochat.app.model.util.QuotationApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Author      : FJ
 * CreateDate  : 2019/07/17 11:03
 * Description :
 */

public class DebugView  extends Dialog {
    
    private ListView configListView;
    private Context context;
    private List<Config> configList;
    private static LinkedHashMap<String, Config> configMap = new LinkedHashMap<>();
    
    public DebugView(@NonNull Context context) {
        super(context, R.style.TransparentDialog);
        this.context = context;
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_debug_dialog);
        configListView = findViewById(R.id.debug_list_view);
        
        findViewById(R.id.debug_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        
        configList = getConfigs();
        
        configListView.setAdapter(new CommonAdapter<Config>(context, R.layout.item_app_config_content, configList) {
            @Override
            protected void convert(ViewHolder viewHolder, final Config item, final int position) {
                viewHolder.setText(R.id.config_content_title, item.getTitle());
                viewHolder.setText(R.id.config_content_input, item.getContent());
                final EditText input = viewHolder.getView(R.id.config_content_input);
                input.setHint(item.getHint());
                
                final Button btn = viewHolder.getView(R.id.config_content_btn);
                viewHolder.setOnClickListener(R.id.config_content_btn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(input.isEnabled()){
                            input.setEnabled(false);
                            btn.setText("编辑");
                            configList.get(position).setContent(input.getText().toString());
                            
                            Config config = configMap.get(item.getTitle());
                            if(config != null){
                                config.setContent(input.getText().toString());
                            }
                            SharePreferenceUtil.saveEntity("debug_config", new ConfigList(configList));
                        } else {
                            input.setEnabled(true);
                            btn.setText("保存");
                        }
                    }
                });
            }
        });
    }
    
    public static class Config {
        private String title;
        private String content;
        private String hint;
        private DebugValueProvider provider;
        
        public Config() {
        }

        public Config(String title, String content, String hint, DebugValueProvider provider) {
            this.title = title;
            this.content = content;
            this.hint = hint;
            this.provider = provider;
        }

        public String getHint() {
            return hint;
        }

        public void setHint(String hint) {
            this.hint = hint;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
            if(provider != null){
                provider.setValue(content);
            }
        }

        public DebugValueProvider getProvider() {
            return provider;
        }

        public void setProvider(DebugValueProvider provider) {
            this.provider = provider;
        }
    }
    
    public static class ConfigList{
        private List<Config> configList;

        public ConfigList() {
        }

        public ConfigList(List<Config> configList) {
            this.configList = configList;
        }

        public List<Config> getConfigList() {
            return configList;
        }
    }
    
    public static void insertConfigs(List<Config> configs){
        List<Config> savedConfigs = getConfigs();
        for (Config config : configs){
            configMap.put(config.getTitle(), config);
        }
        for (Config config : savedConfigs) {
            if(configMap.containsKey(config.getTitle())){
                Config cfg = configMap.get(config.getTitle());
                if(cfg != null){
                    cfg.setContent(config.getContent());
                   
                    ALog.d("set api " + Api.BASE_URL);
                }
            }
        }
        configs.clear();
        configs.addAll(configMap.values());
        SharePreferenceUtil.saveEntity("debug_config", new ConfigList(configs));
    }
    
    private static Gson gson;
    
    static {
        gson = new GsonBuilder().registerTypeAdapter(DebugView.DebugValueProvider.class, new JsonDeserializer<DebugValueProvider>() {
            @Override
            public DebugView.DebugValueProvider deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                if(jsonObject.has("debugType")) {
                    return context.deserialize(json, QuotationApi.class);
                } else {
                    return context.deserialize(json, Api.class);
                }
            }
        }).create();
    }
    
    public static List<Config> getConfigs(){
        ConfigList configList = SharePreferenceUtil.getEntity("debug_config", ConfigList.class, gson);
        if(configList == null){
            configList = new ConfigList(new ArrayList<Config>());
        }
        return configList.getConfigList();
    }
    
    public interface DebugValueProvider{
        String getValue();
        void setValue(String value);
    }
}
