/*
 * Copyright (c) 2008 - 2013 10gen, Inc. <http://10gen.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mongodb.codecs;

import com.mongodb.DBEncoderFactory;
import com.mongodb.DBObject;
import org.bson.BSONWriter;
import org.mongodb.Encoder;
import org.mongodb.codecs.ObjectIdGenerator;

public class DBEncoderFactoryAdapter implements Encoder<DBObject> {

    private final DBEncoderFactory encoderFactory;

    public DBEncoderFactoryAdapter(final DBEncoderFactory encoderFactory) {
        this.encoderFactory = encoderFactory;
    }

    @Override
    public void encode(final BSONWriter bsonWriter, final DBObject value) {
        new DBEncoderAdapter(encoderFactory.create(), new ObjectIdGenerator()).encode(bsonWriter, value);
    }

    @Override
    public Class<DBObject> getEncoderClass() {
        return DBObject.class;
    }
}