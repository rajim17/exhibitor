/*
 * Copyright 2012 Netflix, Inc.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.netflix.exhibitor.core.backup.s3;

import com.google.common.io.Files;
import org.apache.curator.utils.CloseableUtils;
import org.testng.annotations.AfterClass;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TestS3BackupBigFile extends TestS3BackupProviderBase
{
    public TestS3BackupBigFile() throws IOException
    {
        super(getFile());
    }

    private static File getFile() throws IOException
    {
        File        f = Filer.getFile();
        byte[]      bytes = Files.toByteArray(f);
        
        File            tempFile = File.createTempFile("temp", ".tmp");
        OutputStream    out = new FileOutputStream(tempFile);
        try
        {
            for ( int i = 0; i < 100; ++i )
            {
                out.write(bytes);
            }
        }
        finally
        {
            CloseableUtils.closeQuietly(out);
        }

        return tempFile;
    }

    @AfterClass
    public void     teardown()
    {
        //noinspection ResultOfMethodCallIgnored
        sourceFile.delete();
    }
}
