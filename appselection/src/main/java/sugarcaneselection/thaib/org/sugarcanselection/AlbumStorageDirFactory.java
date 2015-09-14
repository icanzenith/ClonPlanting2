package sugarcaneselection.thaib.org.sugarcanselection;

import java.io.File;

abstract class AlbumStorageDirFactory {
    public abstract File getAlbumStorageDir(String albumName);
}
