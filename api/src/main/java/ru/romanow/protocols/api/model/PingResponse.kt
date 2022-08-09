package ru.romanow.protocols.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.xml.bind.annotation.XmlRootElement;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement
public class PingResponse {
    private String response;
}
