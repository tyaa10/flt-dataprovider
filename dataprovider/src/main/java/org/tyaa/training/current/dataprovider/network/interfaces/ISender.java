package org.tyaa.training.current.dataprovider.network.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;

import java.util.List;

public interface ISender {
    void send(List<IExportModel> data, String uri) throws JsonProcessingException;
}
