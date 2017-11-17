package com.mycompany.myapp.service.dto;

import com.mycompany.myapp.domain.Album;

public class AlbumRateStats {

    private Album album;
    private Double avg;
    private Integer max;
    private Integer min;

        public AlbumRateStats(Album album, Double avg, Integer max, Integer min) {
                this.album = album;
                this.avg = avg;
                this.max = max;
                this.min = min;
            }

}
