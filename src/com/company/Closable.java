package com.company;

import java.io.IOException;

public interface Closable extends AutoCloseable{
    public void close() throws IOException;
}
