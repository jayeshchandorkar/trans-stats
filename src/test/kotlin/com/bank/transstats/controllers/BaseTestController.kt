package com.bank.transstats.controllers

import com.nhaarman.mockito_kotlin.spy
import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.beans.factory.support.DefaultListableBeanFactory
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.validation.Validator
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean
import org.springframework.validation.beanvalidation.SpringConstraintValidatorFactory


@RunWith(MockitoJUnitRunner::class)
abstract class BaseTestController<C> {

    protected abstract var controller: C

    protected val validatorBean = LocalValidatorFactoryBean()
    protected val customBeanFactory = DefaultListableBeanFactory()
    protected val spyBeanFactory = spy(customBeanFactory)

    protected lateinit var mockMvc: MockMvc

    @Mock
    lateinit var validator: Validator

    @Before
    fun setUp() {
        validatorBean.constraintValidatorFactory = SpringConstraintValidatorFactory(spyBeanFactory)

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setValidator(validatorBean)
                .build()
    }

}