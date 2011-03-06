package org.openehealth.tutorial

import org.apache.camel.spring.SpringRouteBuilder
import org.openehealth.ipf.platform.camel.core.config.CustomRouteBuilder;

public class SampleRouteBuilder extends CustomRouteBuilder {

  void configure() {

    from('jetty:http://0.0.0.0:8800/tutorial')
        .convertBodyTo(String.class)
        .to('direct:input1')
    //.multicast().to('direct:file-save','direct:reverse-response')

    from('direct:input1')
        .transmogrify { it * 2 }
        .setFileHeaderFrom('destination')
        .to('file:target/output')

    from('direct:input2')
        .reverse()

    from('direct:file-save')
        .setFileHeaderFrom('destination')
        .to('file:target/output')
  }
}
