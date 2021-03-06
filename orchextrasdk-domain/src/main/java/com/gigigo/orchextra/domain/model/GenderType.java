/*
 * Created by Orchextra
 *
 * Copyright (C) 2016 Gigigo Mobile Services SL
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gigigo.orchextra.domain.model;


public enum GenderType implements StringValueEnum {
    MALE("m"),
    FEMALE("f"),
    ND("n");

    private final String text;

    GenderType(final String text) {
        this.text = text;
    }

    @Override
    public String getStringValue() {
        return text;
    }

    public static GenderType getTypeFromString(String gender) {
        for (GenderType genderType : GenderType.values()) {
            if (genderType.getStringValue().equals(gender)) {
                return genderType;
            }
        }
        return ND;
    }
}
