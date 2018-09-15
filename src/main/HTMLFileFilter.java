package main;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class HTMLFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        boolean result = false;
        String _html = ".html";
        String _htm = ".htm";

        boolean isdDotHtml =  f.getName().substring(f.getName().lastIndexOf(".")).equalsIgnoreCase(_html);
        boolean isdDotHtm =  f.getName().substring(f.getName().lastIndexOf(".")).equalsIgnoreCase(_htm);
        if( f.isDirectory() || isdDotHtml  || isdDotHtm )
            result = true;
        return result;
    }

    @Override
    public String getDescription() {
        return "HTML и HTM файлы";
    }
}
