package com.x75f.installer.Utils;

import com.google.api.client.json.GenericJson;
import com.google.api.client.util.Key;

import java.io.Serializable;

/**
 * Created by JASPINDER on 7/11/2016.
 */
public class NotesPreviewData extends GenericJson implements Serializable {

    @Key("_id")
    private String id;
    @Key("ccu_id")
    private String ccu_id;
    @Key("preview_text")
    private String preview_text;
    @Key("modified_time")
    private String modified_time;

    public String getModified_time() {
        return modified_time;
    }

    public String getId() {
        return id;
    }

    public String getCcu_id() {
        return ccu_id;
    }

    public String getPreview_text() {
        return preview_text;
    }
}
