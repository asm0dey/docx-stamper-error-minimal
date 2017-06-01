package docxstamer.error;

import org.apache.commons.lang3.RandomStringUtils;
import org.wickedsource.docxstamper.DocxStamper;
import org.wickedsource.docxstamper.replace.typeresolver.AbstractToTextResolver;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by finkel on 01.06.17.
 */
public class Error {
    DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("\"dd\" MMMM yyyy г.");
    private DocxStamper<Big> stamper = new DocxStamper<>();

    {
        stamper.getTypeResolverRegistry().registerTypeResolver(LocalDate.class, new AbstractToTextResolver<LocalDate>() {
            @Override
            protected String resolveStringForObject(LocalDate object) {
                return DATE_FORMAT.format(object);
            }
        });
    }

    public void exec() throws IOException {

        for (int i = 0; i < 10; i++) {
            File test = File.createTempFile("test", ".docx");
            stamper.stamp(this.getClass().getClassLoader().getResourceAsStream("error.docx"), new Big(), new FileOutputStream(test));
            System.out.println("test = " + test);
        }
    }

    static class Big {
        public List<Info> infos = new ArrayList<>();

        public Big() {
            for (int i = 0; i < 10; i++) {
                infos.add(new Info());
            }
        }
    }

    static class Info {
        public LocalDate date = LocalDate.now();
        public String random = RandomStringUtils.randomAlphanumeric(20);
    }
}
