# Organization Hierarchy Management System
This project implements a tree-based data structure to manage the hierarchy of employees in a company. The system allows for efficient operations such as hiring and firing employees, determining the level of an employee, finding the immediate boss, identifying the lowest common boss of two employees, and generating a string representation of the organization rooted at a specific employee.

## Overview
The project defines a Java class OrgHierarchy that implements the OrgHierarchyInterface. The core data structure is a binary search tree (BST) augmented with additional information to support efficient operations. Each node in the tree represents an employee, and the tree is structured based on the hierarchy levels.

## Key Features
1. Hire Owner: Adds the first employee (owner) to the organization. There is only one owner, and attempting to hire another owner will result in an exception.

2. Hire Employee: Adds a new employee to the organization, specifying the boss under whom the new employee will work. The level of the new employee is automatically determined based on the boss's level.

3. Fire Employee: Deletes an employee who does not manage any other employees. If the employee is the owner, it throws an exception.

4. Fire Employee with Management Transfer: Deletes an employee who might manage other employees. Transfers the employees working under the deleted employee to another employee at the same level.

5. Get Level: Returns the level of an employee given their ID.

6. Get Boss: Returns the immediate boss of an employee. Returns -1 if the employee is the owner.

7. Lowest Common Boss: Finds the lowest common boss of two employees. Returns -1 if either of the input IDs is the owner.

8. Organization Size: Returns the number of employees in the organization.

9. Check Empty Organization: Returns true if the organization is empty.

10. Generate Organization Tree String: Returns a string representation of the organization rooted at a specific employee. The string contains employee IDs organized level-wise.

## Efficiency Considerations
The project aims to implement each method in O(log(n)) time complexity, and O(1) time complexity wherever applicable. The underlying AVL tree structure ensures efficient balancing and log(n) height, contributing to overall efficient operations.
