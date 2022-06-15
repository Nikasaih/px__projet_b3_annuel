package com.backend.imgmc.common.exception;

public class ImgExtensionNotAllowedExc extends RuntimeException {
    public ImgExtensionNotAllowedExc() {
        super("extension not allowed");
    }
}
