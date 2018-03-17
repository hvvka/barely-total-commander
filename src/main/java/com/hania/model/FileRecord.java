package com.hania.model;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="mailto:226154@student.pwr.edu.pl">Hanna Grodzicka</a>
 */
public class FileRecord {

    private Map<String, WeakReference<CachedImage>> records;

    public FileRecord() {
        records = new HashMap<>();
    }

    public FileRecord(Map<String, WeakReference<CachedImage>> records) {
        this.records = records;
    }

    public void setRecords(Map<String, WeakReference<CachedImage>> records) {
        this.records = records;
    }

    public Map<String, WeakReference<CachedImage>> getRecords() {
        return records;
    }

    public void putFileRecord(String path, CachedImage cachedImage) {
        WeakReference<CachedImage> imageWeakReference = new WeakReference<>(cachedImage);
        records.put(path, imageWeakReference);
    }
}
