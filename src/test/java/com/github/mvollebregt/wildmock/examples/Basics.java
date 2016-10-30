/*
 * Copyright 2016 Michel Vollebregt
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.mvollebregt.wildmock.examples;

import org.junit.jupiter.api.BeforeEach;

import static com.github.mvollebregt.wildmock.Wildmock.mock;

/**
 * Examples used in the Basics section of the documentation.
 */
@SuppressWarnings("WeakerAccess")
public class Basics {

    public class ReportService {

        private SalesRepository salesRepository;

        public void setSalesRepository(SalesRepository salesRepository) {
            this.salesRepository = salesRepository;
        }
    }

    public interface SalesRepository {

    }

    private ReportService reportService = new ReportService();
    private SalesRepository salesRepository = mock(SalesRepository.class);

    @BeforeEach
    public void setUp() {
        reportService.setSalesRepository(salesRepository);
    }


}