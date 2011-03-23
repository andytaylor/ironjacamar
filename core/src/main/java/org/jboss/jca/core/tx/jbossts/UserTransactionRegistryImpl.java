/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.jca.core.tx.jbossts;

import org.jboss.jca.core.spi.transaction.usertx.UserTransactionListener;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jboss.logging.Logger;

/**
 * UserTransactionRegistry implementation
 * 
 * @author <a href="jesper.pedersen@jboss.org">Jesper Pedersen</a>
 */
public class UserTransactionRegistryImpl implements org.jboss.jca.core.spi.transaction.usertx.UserTransactionRegistry
{
   /** The logger */
   private static Logger log = Logger.getLogger(UserTransactionRegistryImpl.class);

   /** The delegator */
   private org.jboss.tm.usertx.UserTransactionRegistry delegator;

   /** Listener map */
   private Map<UserTransactionListener, UserTransactionListenerImpl> listeners;

   /**
    * Constructor
    * @param delegator The delegator instance
    */
   public UserTransactionRegistryImpl(org.jboss.tm.usertx.UserTransactionRegistry delegator)
   {
      this.delegator = delegator;
      this.listeners =
         Collections.synchronizedMap(new HashMap<UserTransactionListener, UserTransactionListenerImpl>());
   }

   /**
    * Add a listener
    * @param listener The listener
    */
   public void addListener(UserTransactionListener listener)
   {
      UserTransactionListenerImpl impl = new UserTransactionListenerImpl(listener);

      delegator.addListener(impl);
      listeners.put(listener, impl);
   }
   
   /**
    * Remove a listener
    * @param listener The listener
    */
   public void removeListener(UserTransactionListener listener)
   {
      UserTransactionListenerImpl impl = listeners.get(listener);

      if (impl != null)
      {
         delegator.removeListener(impl);
         listeners.remove(listener);
      }
   }
}