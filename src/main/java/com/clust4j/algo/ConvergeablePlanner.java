/*******************************************************************************
 *    Copyright 2015, 2016 Taylor G Smith
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *******************************************************************************/
package com.clust4j.algo;

interface ConvergeablePlanner extends java.io.Serializable {
	/**
	 * The maximum number of iterations the algorithm
	 * is permitted before aborting without converging
	 * @return max iterations before convergence
	 */
	public int getMaxIter();
	
	/**
	 * This minimum change between iterations that will
	 * denote an iteration as having converged
	 * @return the min change for convergence
	 */
	public double getConvergenceTolerance();
}