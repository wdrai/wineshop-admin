package com.wineshop.services;

import java.util.Map;

import org.granite.messaging.service.annotations.RemoteDestination;
import org.granite.tide.data.DataEnabled;
import org.granite.tide.data.DataEnabled.PublishMode;

import com.wineshop.entities.Vineyard;


@RemoteDestination
@DataEnabled(topic="wineshopTopic", publish=PublishMode.ON_SUCCESS)
public interface WineshopService {

    public void save(Vineyard vineyard);
    
    public void remove(Long vineyardId);
    
    public Map<String, Object> list(Vineyard filter, int first, int max, String[] sort, boolean[] asc);
}
