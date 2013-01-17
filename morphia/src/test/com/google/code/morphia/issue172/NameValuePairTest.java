/*
 * Copyright (c) 2008 - 2012 10gen, Inc. <http://10gen.com>
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

package com.google.code.morphia.issue172;

import com.google.code.morphia.TestBase;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Id;
import com.google.code.morphia.testutil.AssertedFailure;
import org.bson.types.ObjectId;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;

@Ignore("add back when TypeLiteral support is in; issue 175")
public class NameValuePairTest extends TestBase {

    @Test
    public void testNameValuePairWithDoubleIn() throws Exception {
        morphia.map(NameValuePairContainer.class);
        final NameValuePairContainer container = new NameValuePairContainer();
        container.pair = new NameValuePair<Name, Double>(Name.FOO, Double.valueOf(1.2d));
        ds.save(container);

        new AssertedFailure() {
            @Override
            public void thisMustFail() throws Throwable {
                // FIXME : shouldn't fail
                ds.get(container);

            }
        };

    }

    static enum Name {
        FOO, BAR
    }

    @Entity
    private static class NameValuePairContainer {
        @Id
        private ObjectId id;
        NameValuePair<Name, Double> pair;
    }

    @SuppressWarnings("rawtypes")
    private static class NameValuePair<T1 extends Enum<?>, T2> implements Serializable {
        private static final long serialVersionUID = 1L;
        private final T2 value;
        private final T1 name;

        @Deprecated
        private NameValuePair() {
            value = null;
            name = null;
        }

        /**
         *
         */
        public NameValuePair(final T1 name, final T2 value) {
            this.name = name;
            this.value = value;

        }

//		public NameValuePair(final Entry<T1, T2> e) {
//			this(e.getKey(), e.getValue());
//		}
//		
//		public T2 getValue() {
//			return value;
//		}
//		
//		public T1 getName() {
//			return (T1) name;
//		}

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((name == null) ? 0 : name.hashCode());
            result = prime * result + ((value == null) ? 0 : value.hashCode());
            return result;
        }

        @Override
        public boolean equals(final Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final NameValuePair other = (NameValuePair) obj;
            if (name == null) {
                if (other.name != null) {
                    return false;
                }
            }
            else if (!name.equals(other.name)) {
                return false;
            }
            if (value == null) {
                if (other.value != null) {
                    return false;
                }
            }
            else if (!value.equals(other.value)) {
                return false;
            }
            return true;
        }

    }

}
