package com.dtn.quickstart;

import io.quarkus.test.junit.NativeImageTest;

@NativeImageTest
public class NativeGelfLoggingResourceIT extends GelfLoggingResourceTest {

    // Execute the same tests but in native mode.
}