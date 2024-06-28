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
    public String lessonName;
    public String levelName;
    public String nativeLanguageName;
    public String learningLanguageName;
    public Integer sequenceNumber;
    public String word;
    public String translation;
    public String image;
    public String pronunciationAudio;
}
