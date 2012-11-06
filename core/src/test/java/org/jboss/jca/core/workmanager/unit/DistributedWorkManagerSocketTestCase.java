/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.jboss.jca.core.workmanager.unit;

import org.jboss.jca.arquillian.embedded.Configuration;
import org.jboss.jca.core.workmanager.rars.dwm.WorkConnectionFactory;
import org.jboss.jca.embedded.dsl.InputStreamDescriptor;

import java.util.UUID;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.api.spec.ResourceAdapterArchive;

import org.junit.Ignore;
import org.junit.runner.RunWith;


/**
 * DistributedWorkManagerSocketTestCase.
 *
 * Tests for the JBoss specific distributed work manager functionality.
 *
 * @author <a href="mailto:jesper.pedersen@jboss.org">Jesper Pedersen</a>
 */
@Ignore
@RunWith(Arquillian.class)
@Configuration(autoActivate = false)
public class DistributedWorkManagerSocketTestCase extends AbstractDistributedWorkManagerTest
{


   // --------------------------------------------------------------------------------||
   // Deployments --------------------------------------------------------------------||
   // --------------------------------------------------------------------------------||

   /**
    * Define the distributed work manager deployment
    * @return The deployment archive
    */
   @Deployment(name = "DWM", order = 1)
   public static InputStreamDescriptor createDistributedWorkManagerDeployment()
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      InputStreamDescriptor isd = new InputStreamDescriptor("dwm-socket.xml",
                                                            cl.getResourceAsStream("dwm-socket.xml"));
      return isd;
   }

   /**
    * Define the resource adapter deployment
    * @return The deployment archive
    */
   @Deployment(name = "RAR", order = 2)
   public static ResourceAdapterArchive createArchiveDeployment()
   {
      ResourceAdapterArchive raa =
         ShrinkWrap.create(ResourceAdapterArchive.class, "work.rar");

      JavaArchive ja = ShrinkWrap.create(JavaArchive.class, UUID.randomUUID().toString() + ".jar");
      ja.addPackage(WorkConnectionFactory.class.getPackage());

      raa.addAsLibrary(ja);
      raa.addAsManifestResource("rars/dwm/META-INF/ra.xml", "ra.xml");

      return raa;
   }

   /**
    * Define the activation deployment
    * @return The deployment archive
    */
   @Deployment(name = "ACT1", order = 3)
   public static InputStreamDescriptor createActivationDeployment1()
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      InputStreamDescriptor isd = new InputStreamDescriptor("dwm-bc1-ra.xml",
                                                            cl.getResourceAsStream("dwm-bc1-ra.xml"));
      return isd;
   }

   /**
    * Define the activation deployment
    * @return The deployment archive
    */
   @Deployment(name = "ACT2", order = 4)
   public static InputStreamDescriptor createActivationDeployment2()
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      InputStreamDescriptor isd = new InputStreamDescriptor("dwm-bc2-ra.xml",
                                                            cl.getResourceAsStream("dwm-bc2-ra.xml"));
      return isd;
   }
}