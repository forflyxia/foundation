package cn.tcc.foundation.core.lite.io;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;


public abstract class ProxyInputStream extends FilterInputStream {

    ProxyInputStream(InputStream proxy) {
        super(proxy);
    }

    @Override
    public int read()
            throws IOException {
        try {
            beforeRead(1);
            int b = this.in.read();
            afterRead(b != IOUtils.EOF ? 1 : IOUtils.EOF);
            return b;
        } catch (IOException e) {
            handleIOException(e);
        }
        return IOUtils.EOF;
    }

    @Override
    public int read(byte[] bts)
            throws IOException {
        try {
            beforeRead(bts != null ? bts.length : 0);
            int n = this.in.read(bts);
            afterRead(n);
            return n;
        } catch (IOException e) {
            handleIOException(e);
        }
        return IOUtils.EOF;
    }

    @Override
    public int read(byte[] bts, int off, int len)
            throws IOException {
        try {
            beforeRead(len);
            int n = this.in.read(bts, off, len);
            afterRead(n);
            return n;
        } catch (IOException e) {
            handleIOException(e);
        }
        return IOUtils.EOF;
    }

    @Override
    public long skip(long ln)
            throws IOException {
        try {
            return this.in.skip(ln);
        } catch (IOException e) {
            handleIOException(e);
        }
        return 0L;
    }

    @Override
    public int available()
            throws IOException {
        try {
            return super.available();
        } catch (IOException e) {
            handleIOException(e);
        }
        return 0;
    }

    @Override
    public void close()
            throws IOException {
        try {
            this.in.close();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.in.mark(readlimit);
    }

    @Override
    public synchronized void reset()
            throws IOException {
        try {
            this.in.reset();
        } catch (IOException e) {
            handleIOException(e);
        }
    }

    @Override
    public boolean markSupported() {
        return this.in.markSupported();
    }

    private void beforeRead(int n)
            throws IOException {
    }

    private void afterRead(int n)
            throws IOException {
    }

    private void handleIOException(IOException e)
            throws IOException {
        throw e;
    }
}
