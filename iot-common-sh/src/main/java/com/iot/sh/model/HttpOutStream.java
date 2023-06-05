package com.iot.sh.model;

import java.io.OutputStream;

/**
 *
 * @author Kenel Liu*
 */
public class HttpOutStream {
    private OutputStream outputStream;
    private boolean autoCloseStream;
    public HttpOutStream(){
        this(true);
    }
    public HttpOutStream(boolean autoCloseStream){
        this.autoCloseStream=autoCloseStream;
    }
    public OutputStream getOutputStream() {
        return outputStream;
    }

    public void setOutputStream(OutputStream outputStream) {
        this.outputStream = outputStream;
    }
    public boolean isAutoCloseStream() {
        return this.autoCloseStream;
    }
    public void setAutoCloseStream(boolean autoCloseStream) {
        this.autoCloseStream = autoCloseStream;
    }
}
