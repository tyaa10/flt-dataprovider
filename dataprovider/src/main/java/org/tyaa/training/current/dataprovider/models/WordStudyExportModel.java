package org.tyaa.training.current.dataprovider.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tyaa.training.current.dataprovider.models.interfaces.IExportModel;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordStudyExportModel implements IExportModel {
    public Long id;
    public String levelName;
    public String lessonName;
    public String image;
}
