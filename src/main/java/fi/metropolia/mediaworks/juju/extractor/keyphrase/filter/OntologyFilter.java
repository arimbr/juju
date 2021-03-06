/*******************************************************************************
 * Copyright (c) 2013 Olli Alm / Metropolia www.metropolia.fi
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND 
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 ******************************************************************************/
package fi.metropolia.mediaworks.juju.extractor.keyphrase.filter;

import java.util.Set;

import com.google.common.base.Predicate;

import fi.metropolia.mediaworks.juju.document.Token;
import fi.metropolia.mediaworks.juju.extractor.Gram;
import fi.metropolia.mediaworks.juju.persistence.dbtrie.TrieInterface;
import fi.metropolia.mediaworks.juju.util.ResourceContainer;

// to replace totally ontology extractor: if term is contained in ontology, include it to results

public class OntologyFilter implements Predicate<Gram> {
	private TrieInterface vocabulary;
	private ResourceContainer concepts;

	public OntologyFilter(TrieInterface vocab) {
		this.vocabulary = vocab;
		concepts = new ResourceContainer();
	}

	@Override
	public boolean apply(Gram gram) {

		//NOTE, CHECK: vocabulary terms are ordered alphabetically, right?
		Set<String> uris = vocabulary.getIds(gram.getMatchString());
		if (uris != null) {
			for (Token t : gram) {
//				t.uris = new ArrayList<String>(uris);
				concepts.addURI(uris, t.getSentence().getIndex(), t.getIndex());
			}
			return true;
		}
		return false;

	}

	public ResourceContainer results() {
		return concepts;
	}
}
