# Cryptography - Proving Secuirty 
The aim of the first part of the coursework was to implement a given cipher using java libraries that was then tested for performance and accuracy. The finished implementation can be seen in the Java file.

The second part of the coursework was to proove that given some cypher scheme they are not secure. The proof can be seen below. 

## Prooving CBC is not secure against chosen plain-text attacks (IND-CPA)
Assuming the adversary knows the following: IV is always incremented by one by the challenger or the oracle, IV for any ciphertext is appended in plaintext to the ciphertext, then, the following strategy will work to ensure the Adversary wins 100% of the time.  

* The adversary creates two plain text  __*m<sub>0</sub> & m<sub>1</sub>*__ which are equal in length.   
* Adversary sends both plaintexts to the challenger 
* Challenger chooses a uniform bit, __*b ∈ {0, 1}*__ and encrypts __*m<sub>b</sub>*__ as __*c = (IV<sub>i</sub> = i, Ek( m<sub>b</sub> ⊕ IV<sub>i</sub> ))*__
 
* Challenger returns __*c*__ to the adversary and increments __*i*__ by one, this is the __*IV*__ that is going to be used next either by the oracle or challenger. Let’s call this __*IV<sub>o</sub>*__
 
* Since __*IV<sub>i</sub>*__  is given in plaintext in c, the adversary can deduce __*IV<sub>next</sub> = IV<sub>i</sub> + 1*__

* Adversary now sends the plaintext __*p = (m0 ⊕ IV<sub>i</sub> ⊕ IV<sub>next</sub>)*__ to the oracle  
* The oracle returns __*p′ = (IV<sub>o</sub> = i, Ek(p⊕IV<sub>o</sub>)*__. Note, at this stage __*IV<sub>o</sub>= IV<sub>next</sub>*__
* When we simplify __*Ek(p ⊕ IV<sub>o</sub>)*__, we get </br>
__*Ek(p ⊕ IV<sub>o</sub>) = Ek(m<sub>0</sub> ⊕ IV<sub>i</sub> ⊕ (IV<sub>next</sub> ⊕ IV<sub>o</sub>)) = Ek(m<sub>0</sub> ⊕ IV<sub>i</sub> ⊕ 0) = Ek(m<sub>0</sub> ⊕ IV<sub>i</sub>)*__
* Therefore, __*p′= (IV<sub>o</sub>=i, Ek(m<sub>0</sub> ⊕ IV<sub>i</sub> ))*__
* Advisor now has __*p'*__
* If __*p′*__ is the same as c then we can conclude that __*b′*__ must be 0 otherwise b′ must be 1

We can say the adversary wins the game as the probability of b′ being correct is 1 as either way we can always output b′. 

This strategy works by exploiting the fact the IV is predictable as it is incremented by one each time it is used. The fact that the IV is given as plaintext in the ciphertext lets us easily figure out what the next IV is going to be, thus letting us negate the IV for the next message we send to the oracle. By being able to predict the IV it lets us easily compute 
b′
 every time therefore, proving that CBC-mode is not IND-CPA secure when the IV is easily predictable.  
 
 
 ## Prooving a HMAC scheme is not secure
 
The new proposed HMAC schemes increases performance by half for larger messages however it is not secure under the existential forgeability model. I have come up with the experiment below to prove the scheme is not secure.  

Definitions: 

Let m′=m||0 if |m|=odd; otherwise m′=m
 
**Mac<sub>k</sub>(m)= H ((k⊕a)∣∣∣∣H((k⊕b)∣∣∣∣(m′L⊕m′R)))** , where the inputs are m∈ {0, 1}∗, key k 
  
**Vrfy<sub>k</sub>(m,t)=b**
, with b = 1 meaning valid and b = 0 meaning invalid 

Query Phase: 

* Adversary chooses the message m1
 

* Adversary sends  m1 to the oracle and returns tag t such that t=Mack(m1).  

* The adversary now has a valid pair (m1,t) where Vrfy<sub>k</sub>(m1,t)=1 
* Note that m1 ∈ Q or Q = {m1}
 
Forge Phase: 

* Adversary can create m2 such that m2=m′1R⊕m′1L
* Adversary can now use m2 with previous tag t and still pass verification. Vrfy<sub>k</sub>(m2,t)=1
* Therefore, adversary can successfully forge the tag for m2 which is not in Q and thus the scheme is not secure.  

This strategy works because in the new proposed HMAC scheme the message is split into two halves and exclusive-ORed with each other, which implies the order of each half of the message does not matter. However, this is not true since mL||mR≠mR||mL. A secure HMAC function should create completely different hash for both m1& m2 whereas the new proposed HMAC scheme does not.  
