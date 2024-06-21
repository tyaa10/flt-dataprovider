package org.tyaa.training.current.dataprovider.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WordStudyExportModel {
    public Long id;
    public String levelName;
    public String lessonName;
    public String image;
}
