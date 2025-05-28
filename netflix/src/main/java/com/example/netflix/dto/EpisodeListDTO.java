package com.example.netflix.dto;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "episodes")
public class EpisodeListDTO {
    private List<EpisodeDTO> episodes;

    public EpisodeListDTO() {}
    public EpisodeListDTO(List<EpisodeDTO> episodes) { this.episodes = episodes; }

    public List<EpisodeDTO> getEpisodes() { return episodes; }
    public void setEpisodes(List<EpisodeDTO> episodes) { this.episodes = episodes; }
}
