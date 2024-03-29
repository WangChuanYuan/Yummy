package org.casual.yummy.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Embeddable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Anchor {

    // location regex like: 北京市,北京市，西城区
    private String location;

    private String detailLocation;

    private double lng;

    private double lat;
}
