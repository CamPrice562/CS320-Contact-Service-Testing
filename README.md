<div align="center">

# 🧪 Contact Service: Requirements-Driven Unit Testing

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![JUnit5](https://img.shields.io/badge/JUnit5-25A162?style=for-the-badge&logo=junit5&logoColor=white)
![Course](https://img.shields.io/badge/CS320-Software_Test,_Automation_QA-blue?style=for-the-badge)
![Coverage](https://img.shields.io/badge/Coverage-100%25-success?style=for-the-badge)

*A Java contact service built to eight written requirements and proven by 31 JUnit 5 tests, with 100 percent line and branch coverage.*

</div>

---

## 📖 About the Project

Grand Strand Systems, the company in the course scenario, was building a mobile application for a customer and needed three back-end services: contact, task, and appointment. Each one stores objects in memory, with no database and no user interface, and each one had to be verified with JUnit tests against a written list of customer requirements.

I built all three in Project One. This repository holds the contact service, which the portfolio assignment asks for, along with the Project Two summary and reflections report, which covers the testing approach across all three services.

---

## 📂 What Is In This Repo

| File | What it is |
|:-----|:-----------|
| `ContactService/Contact.java` | The contact object. Every field rule lives in its setter, and the constructor routes through those same setters. |
| `ContactService/ContactService.java` | The in-memory service: add with a unique ID, delete by ID, and update the four updatable fields by ID. |
| `ContactService/ContactTest.java` | 20 tests for the five field requirements. |
| `ContactService/ContactServiceTest.java` | 11 tests for the three service requirements. |
| `CS 320 Project Two - Summary and Reflections Report.docx` | The Project Two report: the unit testing approach for all three services, the coverage argument, and reflections on caution, bias, and discipline. |
| `README.md` | This file, including the Module Eight reflection. |

---

## 📋 Requirements to Tests

Every requirement is tested at its allowed boundary and at each way it can be broken.

| Requirement | Tests that prove it |
|:------------|:--------------------|
| Contact ID: required, 10 characters max, not updatable | `testContactIdAtMaxLength`, `testContactIdTooLong`, `testContactIdNull`. Not updatable is enforced by the compiler: the field is `final` and has no setter. |
| First name: required, 10 characters max | `testFirstNameAtMaxLength`, `testFirstNameTooLong`, `testFirstNameNull` |
| Last name: required, 10 characters max | `testLastNameAtMaxLength`, `testLastNameTooLong`, `testLastNameNull` |
| Phone: required, exactly 10 digits | `testPhoneExactlyTenDigits`, `testPhoneTooShort`, `testPhoneTooLong`, `testPhoneNonNumeric`, `testPhoneNull` |
| Address: required, 30 characters max | `testAddressAtMaxLength`, `testAddressTooLong`, `testAddressNull` |
| Service adds contacts with a unique ID | `testAddContact`, `testAddDuplicateIdRejected`, `testAddMultipleContacts`, `testAddNullContact` |
| Service deletes contacts by ID | `testDeleteContact`, `testDeleteLeavesOtherContacts`, `testDeleteMissingContact` |
| Service updates first name, last name, phone, and address by ID | `testUpdateAllUpdatableFields`, `testUpdateMissingContact`, `testUpdateWithInvalidValueRejected` |

The phone row is the one I would point a reviewer to. `testPhoneNonNumeric` passes the ten-character string `"562-253-35"`. A length check accepts it. The requirement does not, so the code uses a pattern match instead of `String.length`.

---

## ✅ Results

Measured with the JUnit Platform console launcher and JaCoCo:

```text
31 tests found
31 tests successful
 0 tests failed

Class            Line    Branch
Contact          100%    100%
ContactService   100%    100%
```

Across all three Project One services, the full suite is 74 tests, all passing, with 100 percent line and branch coverage on every class.

---

## ⚙️ How to Run It

### Prerequisites

* A JDK, version 8 or newer.
* JUnit 5. Eclipse includes it, or you can use the standalone console launcher below.

### Option A: Eclipse

1. Create a Java project and add the JUnit 5 library to its build path.
2. Copy the four files from `ContactService/` into the project's `src` folder. They use the default package.
3. Right-click the project, Run As, JUnit Test. For coverage, use Coverage As, JUnit Test.

### Option B: Command Line

Download `junit-platform-console-standalone-1.11.4.jar` from Maven Central into the repository folder, then:

```bash
javac -d out -cp junit-platform-console-standalone-1.11.4.jar ContactService/*.java
java -jar junit-platform-console-standalone-1.11.4.jar execute -cp out --scan-classpath
```

---

## 🧠 Reflection

### How can I ensure that my code, program, or software is functional and secure?

Functional and secure are separate claims, and each needs its own evidence. Functional means a requirement was met and something proves it, which is what these tests are for. Each requirement is pinned at its boundary and on each side of it, so "exactly ten digits" is proven both by a passing ten-digit value and by a rejected ten-character string that is not all digits.

Secure means the code cannot be pushed into a state the requirements forbid, whether by a careless caller or a hostile one. That drove two decisions here. Each validation rule lives in exactly one place, and the constructor routes through the same setters, so there is no way in that skips a check. The contact ID is `final` with no setter, which turns "shall not be updatable" into a compile-time guarantee instead of a convention. In the appointment service I applied the same idea to dates: `java.util.Date` is mutable, so the class stores and returns defensive copies, because validating a reference you then hand back is not validation at all.

The habit behind all of it comes from my day job in vulnerability management, where a patch doesn't count until a rescan shows the finding is gone. I hold tests to the same standard.

### How do I interpret user needs and incorporate them into a program?

I treat each requirement as the unit of work rather than each method, because one sentence of requirement is often several rules. "The contact object shall have a required phone String field that must be exactly 10 digits" is three rules: the field must exist, it must be ten characters, and every character must be a digit. Reading it as one rule produces a length check that accepts `"562-253-35"`. Reading it as three produces a pattern match and a test that proves the difference.

So my process is to break each stated need into the smallest checkable claims, name a test after each one, and let the tests shape the code. Where a requirement is silent, I build what was asked and nothing more. The appointment service has no update method for exactly that reason. The contact and task requirements list one and the appointment requirements do not, and adding it would have meant delivering something the customer never asked for.

### How do I approach designing software?

I design from the constraints inward. The access pattern in this project is always "find one object by its ID", so each service is a `HashMap` keyed on ID. That makes add, delete, and update constant time, and it enforces the uniqueness requirement for free, since a map cannot hold two values under one key. A `List` would have made every operation a linear scan and bought nothing.

After that, I try to make the correct thing the default and the incorrect thing hard. One rule, one place. A compile-time guarantee over a runtime check whenever the language offers one. A defensive copy wherever a mutable object crosses a boundary. A design decision is good when it removes a whole category of mistake rather than one instance of it, and the cheapest time to make that decision is before the code exists, not after a defect report arrives.

---

## 💼 Transferable Skills

* **Requirements-based test design**: boundary value analysis, equivalence partitioning, and negative testing, with a traceable line from each requirement to its tests.
* **JUnit 5**, including `assertThrows` for rejection cases and `assertAll` for grouped checks.
* **Coverage backed by real assertions**: 100 percent measured with JaCoCo, with tests that check the resulting state instead of just running the code.
* **Defensive Java design**: validation in one place, `final` fields for immutability, and defensive copies of mutable objects.

---

<div align="center">

**Cameron Price** | CS 320: Software Test, Automation QA | Southern New Hampshire University

</div>
