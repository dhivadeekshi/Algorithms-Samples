from Ex6Digraph import Digraph
from Ex7DepthFirstSearchDigraph import DepthFirstSearchDigraph


class Regex:

    def __init__(self, pattern):
        self.m = len(pattern)

        self.char_array = list(pattern)
        print(self.char_array)

        self.digraph = Digraph(self.m + 1)
        ops = []

        for i in range(self.m):
            lp = i

            if pattern[i] == '(' or pattern[i] == '|':
                ops.append(i)
            elif pattern[i] == ')':
                or_i = ops.pop()
                if self.char_array[or_i] == '|':
                    lp = ops.pop()
                    self.digraph.add_edge(or_i, i)
                    self.digraph.add_edge(lp, or_i + 1)
                else:
                    lp = or_i

            if i < self.m - 1 and pattern[i + 1] == '*':
                self.digraph.add_edge(lp, i + 1)
                self.digraph.add_edge(i + 1, lp)

            if pattern[i] == '(' or pattern[i] == '*' or pattern[i] == ')':
                self.digraph.add_edge(i, i + 1)

    def recognizes(self, text):
        dfs = DepthFirstSearchDigraph(self.digraph, 0)
        pc = []
        for i in range(self.digraph.get_v()):
            if dfs.marked[i]:
                pc.append(i)

        for i in range(len(text)):

            matched = []
            for v in pc:
                if v == self.m:
                    continue
                if self.char_array[v] == text[i]:
                    matched.append(v + 1)

            dfs = DepthFirstSearchDigraph(self.digraph, matched)
            pc = []
            for v in range(self.digraph.get_v()):
                if dfs.marked[v]:
                    pc.append(v)

        for v in pc:
            if v == self.m:
                return True
        return False


def test_regex_search():
    pattern = "((A*B|AC)D)"

    regex = Regex(pattern)
    print(f"length: {len(pattern)}")
    print(f'Digraph: {regex.digraph}')
    for text in ["ABCDE", "ACDE", "BCDE", "ABCDE", "ABCDCDE", "CDAABE",
                 "AABD", "ABD", "ACD", "AAAAAAAAABD", "AABEDDE"]:
        print(f"recognizes '{text}'? {regex.recognizes(text)}")


if __name__ == '__main__':
    test_regex_search()
