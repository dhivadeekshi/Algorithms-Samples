class Node(object):
    def __init__(self, value):
        self.value = value
        self.left = None
        self.right = None


class BST(object):
    def __init__(self, root):
        self.root = Node(root)

    def insert(self, new_val):
        self.__insert__(self.root, new_val)

    def search(self, find_val):
        return self.__search__(self.root, find_val)

    def __insert__(self, node, new_val):
        if node is None:
            return Node(new_val)

        if node.value < new_val:
            node.right = self.__insert__(node.right, new_val)
        elif new_val < node.value:
            node.left = self.__insert__(node.left, new_val)
        return node

    def __search__(self, node, find_val):
        if node is None:
            return False

        if node.value < find_val:
            return self.__search__(node.right, find_val)
        elif find_val < node.value:
            return self.__search__(node.left, find_val)
        return True


# Set up tree
tree = BST(4)

# Insert elements
tree.insert(2)
tree.insert(1)
tree.insert(3)
tree.insert(5)

# Check search
# Should be True
print(tree.search(4))
# Should be False
print(tree.search(6))
