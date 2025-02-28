package server.mail.docs;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class Questions {

    private String id;
    private String type;
    private String text;
    private List<String> options = new ArrayList<>();

}
