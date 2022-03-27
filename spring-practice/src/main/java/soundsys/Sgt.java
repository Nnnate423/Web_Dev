package soundsys;

import org.springframework.stereotype.Component;

@Component
public class Sgt implements CompactDisc{
    private String title = "soundsys.Sgt";
    private String artist = "Beatles";
    public void play() {
        System.out.println(title + " plays: "+ artist);
    }
}
